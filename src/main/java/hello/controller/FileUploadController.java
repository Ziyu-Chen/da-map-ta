package hello.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.io.InputStreamReader;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Iterator;
// import java.util.regex.Pattern;
// import java.util.stream.IntStream;

// import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

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

	@RequestMapping(value = "/filters", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<List<String>> getFilters(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "cols", required = true) String cols) throws IOException {
		String fileName = file.getOriginalFilename();
		List<Integer> filteredColumns = Arrays.asList(cols.split(":")).stream().map(s -> Integer.valueOf(s))
				.collect(Collectors.toList());
		List<HashSet<String>> filters = new ArrayList<HashSet<String>>();
		if (fileName.endsWith(".csv")) {
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				isr = new InputStreamReader(file.getInputStream());
				br = new BufferedReader(isr);
				String line = null;
				String[] columnNames = br.readLine().split(",");
				for (int i = 0; i < filteredColumns.size(); i++) {
					HashSet<String> filter = new HashSet<String>();
					filters.add(filter);
				}
				int numCol = columnNames.length;
				int numRow = 0;
				while ((line = br.readLine()) != null) {
					numRow++;
					String[] currentRow = line.split(",");
					if (currentRow.length != numCol) {
						continue;
					}
					Iterator<HashSet<String>> iterator = filters.iterator();
					for (Integer col : filteredColumns) {
						iterator.next().add(currentRow[col]);
					}
				}
				List<List<String>> result = new ArrayList<List<String>>();
				Iterator<HashSet<String>> iterator = filters.iterator();
				int counter = 0;
				while (iterator.hasNext()) {
					List<String> options = new ArrayList<String>(iterator.next());
					options.add(0, columnNames[filteredColumns.get(counter)]);
					if (options.size() < numRow / 4) {
						result.add(options);
					} else {
						List<String> columnName = new ArrayList<String>();
						columnName.add(columnNames[filteredColumns.get(counter)]);
						result.add(columnName);
					}
					counter++;
				}
				return result;
			} catch (IOException e) {
				System.out.println("Something is not right");
			}
		}
		List<List<String>> a = new ArrayList<List<String>>();
		return a;
	}
}
