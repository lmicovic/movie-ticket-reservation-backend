package application.configuration;

import java.util.Base64;

import application.other.Day;

public class Util {

	public static String encodeImage(byte[] fileData) {

		try {

			// Encoding Bytes Image Data to String
			String encodedImageData = Base64.getEncoder().encodeToString(fileData);

			return encodedImageData;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static String decodeImage(String encodedImageString) {

		try {

			byte[] decodedImageDataByte = Base64.getDecoder().decode(encodedImageString.getBytes());

			System.out.println(decodedImageDataByte.length);

			return new String(decodedImageDataByte);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int getDayNumber(Day day) throws Exception {

		switch (day) {
		case Sunday:
			return 0;
		case Monday:
			return 1;
		case Thuesday:
			return 2;
		case Wednesday:
			return 3;
		case Thursday:
			return 4;
		case Friday:
			return 5;
		case Saturday:
			return 6;
		default:
			throw new Exception("Wrong Day: " + day);
		}

	}
	
	public static Day getDayName(int dayNumber) throws Exception {
		
		switch (dayNumber) {
		case 0:
			return Day.Sunday;
		case 1:
			return Day.Monday;
		case 2:
			return Day.Thuesday;
		case 3:
			return Day.Wednesday;
		case 4:
			return Day.Thursday;
		case 5:
			return Day.Friday;
		case 6:
			return Day.Saturday;
		default:
			throw new Exception("Wrong Day Number: " + dayNumber);
		}
		
	}

}
