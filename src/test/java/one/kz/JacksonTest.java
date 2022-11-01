package one.kz;

import one.kz.model.Student;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTest {

    @Test
    void jsonTestJackson() throws Exception {
        File file = new File("src/test/resources/student.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(file, Student.class);
        assertThat(student.name).isEqualTo("Alex");
        assertThat(student.age).isEqualTo(25);
        assertThat(student.isStudent).isTrue();
        assertThat(student.hobbies[0]).isEqualTo("music");
        assertThat(student.passport.number).isEqualTo(123456789);

    }
}
