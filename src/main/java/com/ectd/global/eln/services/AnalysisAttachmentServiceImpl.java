package com.ectd.global.eln.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.AnalysisAttachmentDao;
import com.ectd.global.eln.dto.AnalysisAttachmentDto;
import com.ectd.global.eln.request.AnalysisAttachment;
import com.ectd.global.eln.request.FileInfo;

@Service
public class AnalysisAttachmentServiceImpl implements AnalysisAttachmentService {

	@Autowired
	AnalysisAttachmentDao analysisAttachmentDao;
	
	@Value("${file-path}")
	String filePath;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public AnalysisAttachmentDto getAnalysisAttachmentById(Integer analysisAttachmentId) {
		return analysisAttachmentDao.getAnalysisAttachmentById(analysisAttachmentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AnalysisAttachmentDto> getAnalysisAttachments() {
		return analysisAttachmentDao.getAnalysisAttachments(null, null);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<FileInfo> createAnalysisAttachment(AnalysisAttachment analysisAttachment) {
		this.save(analysisAttachment);
		analysisAttachmentDao.createAnalysisAttachment(analysisAttachment);
		return this.getAnalysisAttachments(Integer.parseInt(analysisAttachment.getExperimentId()));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysisAttachment(AnalysisAttachment analysisAttachment) {
		return analysisAttachmentDao.updateAnalysisAttachment(analysisAttachment);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean deleteAnalysisAttachment(AnalysisAttachment analysisAttachment) {
		AnalysisAttachmentDto experimentAttachmentDto = this.getAnalysisAttachmentById(analysisAttachment.getAnalysisAttachmentId());
		boolean result = false;
		try {
			result = Files.deleteIfExists(Paths.get(experimentAttachmentDto.getAttachmentLocation()));
		} catch (IOException e) {
			throw new RuntimeException("Could not delete the file!");
		}
		analysisAttachmentDao.deleteAnalysisAttachment(analysisAttachment);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<FileInfo> getAnalysisAttachments(Integer experimentId) {
		List<AnalysisAttachmentDto> experimentAttachments = analysisAttachmentDao.getAnalysisAttachments(experimentId, null);
		List<FileInfo> files = new ArrayList<FileInfo>();
		try {
			files = experimentAttachments.stream().map(path -> {
				String filename = path.getAttachmentLocation().substring(path.getAttachmentLocation().lastIndexOf("/") + 1);
				String url = path.getAttachmentLocation();

				return new FileInfo(filename, url, path.getAnalysisExperimentId(), path.getAnalysisAttachmentId());
			}).collect(Collectors.toList());

		} catch (Exception e) {
			throw new RuntimeException("Could not load the files!");
		}

		return files;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Resource getAnalysisAttachmentContent(String fileName, Integer experimentId, Integer projectId) {
		try {
			Path root = Paths.get(this.filePath + "/" +projectId +"/" + experimentId + "/") ;
			Path file = root.resolve(fileName);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	private void save(AnalysisAttachment experimentAttachment) {
		try {
			String dirPath = this.filePath + "/" + experimentAttachment.getProjectId() + "/" + experimentAttachment.getExperimentId() + "/";
			Path root = Paths.get(dirPath);
			root = Files.createDirectories(root);

			Files.copy(experimentAttachment.getFile().getInputStream(), root.resolve(experimentAttachment.getFile().getOriginalFilename()));
			experimentAttachment.setAttachmentLocation(dirPath + experimentAttachment.getFile().getOriginalFilename());
		} catch (Exception e) {
			if (e instanceof FileAlreadyExistsException) {
				throw new RuntimeException("A file of that name already exists.");
			}

			throw new RuntimeException(e.getMessage());
		}
	}
	
}
