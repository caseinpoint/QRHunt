package com.drue.qr.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.drue.qr.models.Game;
import com.drue.qr.services.FileStorageService;
import com.drue.qr.services.QRService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

@RestController
@RequestMapping("/api")
public class APIController {
	private final FileStorageService fileService;
	private final QRService qrService;
	private final QRCodeWriter qrWriter;
	private final MultiFormatReader qrReader;
	private final String URL_PREFIX = "https://13.59.198.223/games/";
	
	public APIController(QRService qrService, FileStorageService fileService) {
		this.fileService = fileService;
		this.qrService = qrService;
		qrWriter = new QRCodeWriter();
		qrReader = new MultiFormatReader();
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/games/{id}/start")
	public ResponseEntity<FileSystemResource> getStartQR(@PathVariable("id") Long id,
			HttpServletRequest request) throws WriterException, IOException {
		BitMatrix matrix = qrWriter.encode(URL_PREFIX + id, BarcodeFormat.QR_CODE, 350, 350);
		File temp = fileService.getFileStorageLocation().resolve("start.png").toFile();
		MatrixToImageWriter.writeToFile(matrix, "PNG", temp);
		FileSystemResource resource = new FileSystemResource(temp);
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/games/{id}/end")
	public ResponseEntity<FileSystemResource> getEndQR(@PathVariable("id") Long id,
			HttpServletRequest request) throws WriterException, IOException {
		Game game = qrService.findGameById(id);
		BitMatrix matrix = qrWriter.encode(game.getQrText(), BarcodeFormat.QR_CODE, 350, 350);
		File temp = fileService.getFileStorageLocation().resolve("end.png").toFile();
		MatrixToImageWriter.writeToFile(matrix, "PNG", temp);
		FileSystemResource resource = new FileSystemResource(temp);
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PostMapping("/games/{game_id}/scan/{user_id}")
	public String scanQR(@PathVariable("game_id") Long game_id, @PathVariable("user_id") Long user_id, @RequestParam("file") MultipartFile mFile) {
		File temp = fileService.getFileStorageLocation().resolve("blob.png").toFile();
		BufferedImage bufferedImage;
		try {
			mFile.transferTo(temp);
			bufferedImage = ImageIO.read(temp);
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "__error__";
		}
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		
		Result result;
		try {
			result = qrReader.decode(bitmap);
//			System.out.println("Scanned result: " + result.getText());
		} catch (NotFoundException e) {
//			System.out.println("No QR code in image");
			return "__none__";
		}
		return qrService.playGame(game_id, user_id, result.getText());
	}
	
	public FileStorageService getFileService() {
		return fileService;
	}
}
