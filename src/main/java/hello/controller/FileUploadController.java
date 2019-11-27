package hello.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
// import java.io.InputStreamReader;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public String[][] fileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "cols", required = false) String cols) throws IOException {
		String fileName = file.getOriginalFilename();

		List<Integer> columns = Arrays.asList(cols.split(":")).stream().map(s -> Integer.valueOf(s))
				.collect(Collectors.toList());

		if (fileName.endsWith(".csv")) {
			byte[] bytes = file.getBytes();
			String completeData = new String(bytes);
			String[] rows = completeData.split("\n");
			int numCol = rows[0].split(",").length;
			String[][] filteredRows = {};

			for (int i = 0; i < rows.length; i++) {
				String[] currentRow = rows[i].split(",");
				List<String> filteredRow = new ArrayList<String>();
				for (int j = 0; j < columns.size(); j++) {
					filteredRow.add(currentRow[columns.get(j)]);
				}
				filteredRows = ArrayUtils.add(filteredRows, filteredRow.toArray(new String[0]));
			}

			return filteredRows;
		}
		String[][] a = { { "a" } };
		return a;
	}

	public String index() {
		return "Fucking Shit!";
	}

}
