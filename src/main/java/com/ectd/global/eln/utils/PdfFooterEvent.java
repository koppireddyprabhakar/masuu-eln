package com.ectd.global.eln.utils;

import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


public class PdfFooterEvent extends PdfPageEventHelper{
	 private Image logo;

	    public PdfFooterEvent() {
	        try {
	            // Load the logo from the classpath
	            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/images/Logo.png");
	            if (logoStream == null) {
	                throw new FileNotFoundException("File not found: static/images/Logo.png");
	            }

	            // Create image from stream
	            this.logo = Image.getInstance(logoStream.readAllBytes());
	        } catch (Exception e) {
	            System.err.println("Error loading logo: " + e.getMessage());
	        }
	    }

	    @Override
	    public void onEndPage(PdfWriter writer, Document document) {
	        if (logo == null) {
	            System.err.println("Logo not loaded, skipping footer image.");
	            return;
	        }

	        PdfContentByte canvas = writer.getDirectContent();

	        // Get page dimensions
	        float pageWidth = document.right() - document.left();

	        // Scale logo to 20% of page width (you can tweak this)
	        float targetWidth = pageWidth * 0.2f;
	        float aspectRatio = logo.getWidth() / (float) logo.getHeight();
	        float targetHeight = targetWidth / aspectRatio;

	        logo.scaleAbsolute(targetWidth, targetHeight);

	        // Calculate position: bottom-left corner, with padding
	        float x = document.leftMargin();         // Align with left margin
	        float y = document.bottomMargin() - targetHeight - 10; // Slightly above the bottom margin

	        logo.setAbsolutePosition(x, y);

	        try {
	            canvas.addImage(logo);
	        } catch (Exception e) {
	            System.err.println("Error adding logo to PDF footer: " + e.getMessage());
	        }
	    }

}
