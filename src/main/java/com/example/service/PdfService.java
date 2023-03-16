package com.example.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class PdfService {
	
	public ByteArrayResource generatePdf() throws IOException {
		
		// create new PDF document
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		
		// add image
		PDImageXObject logo = PDImageXObject.createFromFile("src/main/resources/image/logo.png", document);
		PDImageXObject signature = PDImageXObject.createFromFile("src/main/resources/image/signature.png", document);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		// add fount
		PDFont fontBole = PDType0Font.load(document, new File("src/main/resources/fount/THSarabun Bold.ttf"));
		float fontThaiBold = 20f;
		PDFont font = PDType0Font.load(document, new File("src/main/resources/fount/THSarabun.ttf"));
		float fontThai = 16f;
		
		// image
		contentStream.drawImage(logo, 275, 700, 50, 50);
		contentStream.drawImage(signature, 385, 65, 200, 50);

		// square small
		contentStream.addRect(90, 650, 450, -400);
		contentStream.setStrokingColor(Color.BLACK);

		// square big
		contentStream.addRect(60, 675, 500, -500);
		contentStream.setStrokingColor(Color.BLACK);
		contentStream.setNonStrokingColor(Color.white);

		// line ______
		contentStream.setLineWidth(1.0f);
		contentStream.moveTo(90, 630);
		contentStream.lineTo(540, 630);
		contentStream.stroke();

		// line 1|
		contentStream.setLineWidth(1.0f);
		contentStream.moveTo(185, 250);
		contentStream.lineTo(185, 650);
		contentStream.stroke();

		// line 2|
		contentStream.setLineWidth(1.0f);
		contentStream.moveTo(350, 250);
		contentStream.lineTo(350, 650);
		contentStream.stroke();

		// line 3|
		contentStream.setLineWidth(1.0f);
		contentStream.moveTo(455, 250);
		contentStream.lineTo(455, 650);
		contentStream.stroke();

		// start text
		contentStream.beginText();
		// title
		contentStream.setFont(fontBole, fontThaiBold);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(10, 770));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("บริษัท .....");

		// subject
		contentStream.setFont(font, fontThai);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(10, 750));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("บ้านเลขที่ 999 หมู่ที่ 9 อำเภอเมือง จังหวัดกรุงเพทมหานคร ");
		contentStream.setTextMatrix(Matrix.getTranslateInstance(10, 730));
		contentStream.showText("เบอโทร์:088-888-8888");

		//
		contentStream.setFont(fontBole, fontThaiBold);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(445, 765));
		contentStream.setNonStrokingColor(Color.blue);
		contentStream.showText("ใบเสร็จรับเงิน");

		// date
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = "วันที่: " + format.format(new Date());
		contentStream.setFont(fontBole, fontThaiBold);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(445, 740));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText(dateString);

		// text number
		contentStream.setFont(fontBole, fontThaiBold);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(100, 635));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("ลำดับ");

		contentStream.setFont(PDType1Font.HELVETICA, 14);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(115, 610));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("1");

		// text list
		contentStream.setFont(fontBole, fontThaiBold);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(250, 635));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("รายการ");

		contentStream.setFont(PDType1Font.HELVETICA, 14);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(215, 610));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("******xxxxx*****");

		// text count
		contentStream.setFont(fontBole, fontThaiBold);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(365, 635));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("จำนวน");

		contentStream.setFont(PDType1Font.HELVETICA, 14);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(385, 610));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("2");

		// text price
		contentStream.setFont(fontBole, fontThaiBold);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(485, 635));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("ราคา");

		contentStream.setFont(PDType1Font.HELVETICA, 14);
		contentStream.setTextMatrix(Matrix.getTranslateInstance(485, 610));
		contentStream.setNonStrokingColor(Color.black);
		contentStream.showText("150 " + " B.");

		contentStream.endText();
		contentStream.close();

		// PDF document to byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		document.save(baos);
		document.close();

		// create ByteArrayResource from byte array
		ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

		return resource;
	}

}
