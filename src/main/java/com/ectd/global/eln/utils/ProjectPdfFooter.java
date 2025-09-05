package com.ectd.global.eln.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectPdfFooter extends PdfPageEventHelper {
	private Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

	@Override
	public void onEndPage(PdfWriter writer, Document document) {

		Rectangle pageSize = document.getPageSize();
		PdfContentByte cb = writer.getDirectContent();
		PdfPTable footer = new PdfPTable(3);

		try {

			footer.setWidths(new float[] { 2, 2, 1 });
			footer.setTotalWidth(pageSize.getWidth() - document.leftMargin() - document.rightMargin());
			footer.setLockedWidth(true);
			// Draw line
			PdfPCell lineCell = new PdfPCell(new Phrase(""));
			lineCell.setColspan(3);
			lineCell.setBorder(Rectangle.TOP);
			lineCell.setBorderWidthTop(0.7f);
			lineCell.setFixedHeight(2f);
			footer.addCell(lineCell);

			// Spacer row (to add breathing space)

			PdfPCell spacer = new PdfPCell(new Phrase(""));
			spacer.setColspan(3);
			spacer.setFixedHeight(15f); // adjust spacing between line and footer
			spacer.setBorder(Rectangle.NO_BORDER);
			footer.addCell(spacer);

			// --- Logo ---
			Image logo = null;
			try (InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/images/Logo.png")) {

				if (logoStream != null) {
					logo = Image.getInstance(javax.imageio.ImageIO.read(logoStream), null);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			PdfPCell logoCell;
			if (logo != null) {
				logo.scaleToFit(40, 20); // smaller to match text height
				logoCell = new PdfPCell(logo, false);
			} else {
				logoCell = new PdfPCell(new Phrase("MASUU Logo", footerFont));
			}

			logoCell.setBorder(Rectangle.NO_BORDER);
			logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			footer.addCell(logoCell);
			
			// --- Date ---
			PdfPCell dateCell = new PdfPCell(new Phrase("Date: " + new SimpleDateFormat("dd-MM-yyyy")
					.format(new Date()), footerFont));
			
			dateCell.setBorder(Rectangle.NO_BORDER);
			dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			footer.addCell(dateCell);

			// --- Page ---

			PdfPCell pageCell = new PdfPCell(new Phrase("Page " + writer.getPageNumber(), footerFont));
			pageCell.setBorder(Rectangle.NO_BORDER);
			pageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			footer.addCell(pageCell);
			// Fixed Y-position from absolute page bottom

			float yPos = pageSize.getBottom(30); // 30 = distance from bottom edge in points (~1 cm)
			footer.writeSelectedRows(0, -1, document.leftMargin(), yPos + footer.getTotalHeight(), cb);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
