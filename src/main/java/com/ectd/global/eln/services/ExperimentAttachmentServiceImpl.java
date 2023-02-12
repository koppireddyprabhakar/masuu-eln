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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.ectd.global.eln.dao.ExperimentAttachmentDao;
import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.request.ExperimentAttachment;
import com.ectd.global.eln.request.FileInfo;

@Service
@PropertySource(value = {"classpath:/application.properties"})
public class ExperimentAttachmentServiceImpl implements ExperimentAttachmentService {

	@Autowired
	private ExperimentAttachmentDao expermentAttachmentDao;

	@Value("${file-path}")
	String filePath;

	@Override
	public ExperimentAttachmentDto getExperimentAttachmentById(Integer experimentAttachmentId) {
		return expermentAttachmentDao.getExperimentAttachmentById(experimentAttachmentId);
	}

	@Override
	public List<ExperimentAttachmentDto> getExperimentAttachments() {
		return expermentAttachmentDao.getExperimentAttachments(null, null);
	}

	@Override
	public List<FileInfo> createExperimentAttachment(ExperimentAttachment experimentAttachment) {
		this.save(experimentAttachment);
		expermentAttachmentDao.createExperimentAttachment(experimentAttachment);
		return this.getExperimentAttachments(Integer.parseInt(experimentAttachment.getExperimentId()));
	}

	@Override
	public Integer updateExperimentAttachment(ExperimentAttachment experimentAttachment) {
		return expermentAttachmentDao.updateExperimentAttachment(experimentAttachment);
	}

	@Override
	public Boolean deleteExperimentAttachment(ExperimentAttachment experimentAttachment) {
		ExperimentAttachmentDto experimentAttachmentDto = this.getExperimentAttachmentById(experimentAttachment.getExperimentAttachmentId());
		boolean result = false;
		try {
			result = Files.deleteIfExists(Paths.get(experimentAttachmentDto.getAttachmentLocation()));
		} catch (IOException e) {
			throw new RuntimeException("Could not delete the file!");
		}
		expermentAttachmentDao.deleteExperimentAttachment(experimentAttachment);
		return result;
	}

	@Override
	public List<FileInfo> getExperimentAttachments(Integer experimentId) {

		List<ExperimentAttachmentDto> experimentAttachments = expermentAttachmentDao.getExperimentAttachments(experimentId, null);
		List<FileInfo> files = new ArrayList<FileInfo>();
		try {
			files = experimentAttachments.stream().map(path -> {
				String filename = path.getAttachmentLocation().substring(path.getAttachmentLocation().lastIndexOf("/") + 1);
				String url = path.getAttachmentLocation();

				return new FileInfo(filename, url, path.getExperimentId());
			}).collect(Collectors.toList());

		} catch (Exception e) {
			throw new RuntimeException("Could not load the files!");
		}

		return files;
	}

	@Override
	public Resource getExperimentAttachmentContent(String fileName, Integer experimentId, Integer projectId) {
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


	private void save(ExperimentAttachment experimentAttachment) {
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
