package hello.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
// import java.io.InputStreamReader;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class FileUploadController {

	// public String[][] fileUpload(@RequestParam("file") MultipartFile file,
	// @RequestParam(value = "cols", required = false) String cols) throws
	// IOException {
	// String fileName = file.getOriginalFilename();

	// List<Integer> columns = Arrays.asList(cols.split(":")).stream().map(s ->
	// Integer.valueOf(s))
	// .collect(Collectors.toList());

	// if (fileName.endsWith(".csv")) {
	// byte[] bytes = file.getBytes();
	// String completeData = new String(bytes);
	// String[] rows = completeData.split("\n");
	// String[][] filteredRows = {};

	// for (int i = 0; i < rows.length; i++) {
	// String[] currentRow = rows[i].split(",");
	// List<String> filteredRow = new ArrayList<String>();
	// for (int j = 0; j < columns.size(); j++) {
	// filteredRow.add(currentRow[columns.get(j)]);
	// }
	// filteredRows = ArrayUtils.add(filteredRows, filteredRow.toArray(new
	// String[0]));
	// }

	// return filteredRows;
	// }
	// String[][] a = { { "a" } };
	// return a;
	// }
	@RequestMapping(value = "/columns", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<String> getColumns(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		if (fileName.endsWith(".csv")) {
			byte[] bytes = file.getBytes();
			String dataString = new String(bytes);
			String columnString = dataString.substring(0, dataString.indexOf("\n"));
			return Arrays.asList(columnString.split(","));
		}
		List<String> a = new ArrayList<String>();
		return a;
	}

	@RequestMapping(value = "/table", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<List<String>> getTable(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "cols", required = true) String cols) throws IOException {
		String fileName = file.getOriginalFilename();
		List<Integer> filteredColumns = Arrays.asList(cols.split(":")).stream().map(s -> Integer.valueOf(s))
				.collect(Collectors.toList());

		if (fileName.endsWith(".csv")) {
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				isr = new InputStreamReader(file.getInputStream());
				br = new BufferedReader(isr);
				String line = null;
				List<List<String>> strs = new ArrayList<List<String>>();

				String[] columnNames = br.readLine().split(",");
				List<String> filteredColumnNames = new ArrayList<String>();
				for (int j = 0; j < filteredColumns.size(); j++) {
					filteredColumnNames.add(columnNames[filteredColumns.get(j)]);
				}
				strs.add(filteredColumnNames);
				while ((line = br.readLine()) != null) {
					String[] currentRow = line.split(",");
					if (currentRow.length != columnNames.length)
						continue;
					List<String> filteredRow = new ArrayList<String>();
					for (int j = 0; j < filteredColumns.size(); j++) {
						filteredRow.add(currentRow[filteredColumns.get(j)]);
					}
					strs.add(filteredRow);
				}
				return strs;
			} catch (IOException e) {
				System.out.println("Something is not right");
			}
		}
		List<List<String>> a = new ArrayList<List<String>>();
		return a;
	}
}
