package one.kz;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import one.kz.model.Student;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {


    ClassLoader cl = FileParseTest.class.getClassLoader();

    @Test
    void pdfTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/test.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("test.zip"));
        ZipEntry entry;
        while((entry = is.getNextEntry()) != null) {
            if (entry.getName().contains(".pdf")) {
                try (InputStream inputStream = zf.getInputStream(entry)) {
                    PDF pdf = new PDF(inputStream);
                    assertThat(pdf.text).contains("Lorem ipsum");
                }
            }
        }
    }

    @Test
    void xlsTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/test.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("test.zip"));
        ZipEntry entry;
        while((entry = is.getNextEntry()) != null) {
            if (entry.getName().contains(".xls")) {
                try (InputStream inputStream = zf.getInputStream(entry)) {
                    XLS xls = new XLS(inputStream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(1)
                            .getCell(1)
                            .getStringCellValue()).isEqualTo("Dulce");
                }
            }
        }
    }

    @Test
    void csvTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/test.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("test.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            if (entry.getName().contains(".csv")) {
                try(InputStream inputStream = zf.getInputStream(entry);
                    CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                    List<String[]> content = reader.readAll();
                    String[] row = content.get(1);
                    assertThat(row[0]).isEqualTo("microsoft");
                    assertThat(row[1]).isEqualTo("windows");
                }
            }
        }

    }


    @Test
    void jsonTest() {
        InputStream is = cl.getResourceAsStream("student.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Alex");
        assertThat(jsonObject.get("isStudent").getAsBoolean()).isTrue();
        assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(123456789);

    }

    @Test
    void jsonTestWithModel() {
        InputStream is = cl.getResourceAsStream("student.json");
        Gson gson = new Gson();
        Student student = gson.fromJson(new InputStreamReader(is), Student.class);
        assertThat(student.name).isEqualTo("Alex");
        assertThat(student.isStudent).isTrue();
        assertThat(student.passport.number).isEqualTo(123456789);

    }
}
