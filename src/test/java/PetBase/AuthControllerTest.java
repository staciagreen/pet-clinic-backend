package PetBase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void registerAndLoginFlow() throws Exception {
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "username":"testuser1",
                  "password":"pass123",
                  "name":"Тест",
                  "birthDate":"1990-01-01"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser1"));

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuser1","password":"wrong"}
                """))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuser1","password":"pass123"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser1"));
    }

    @Test
    void accessMeWithoutAuth_thenUnauthorized() throws Exception {
        mvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerAndLoginFlow1() throws Exception {
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "username":"testuserr2",
                  "password":"pass123",
                  "name":"Тест",
                  "birthDate":"1990-01-01"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuserr2"));

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuserr2","password":"wrong"}
                """))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuserr2","password":"pass123"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuserr2"));
    }

    @Test
    void accessMeWithoutAuth_thenUnauthorized3() throws Exception {
        mvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerAndLoginFlow3() throws Exception {
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "username":"testuser",
                  "password":"pass123",
                  "name":"Тест",
                  "birthDate":"1990-01-01"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuser","password":"wrong"}
                """))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuser","password":"pass123"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void accessMeWithoutAuth_thenUnauthorized2() throws Exception {
        mvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerAndLoginFlow2() throws Exception {
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "username":"testuser4",
                  "password":"pass123",
                  "name":"Тест",
                  "birthDate":"1990-01-01"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser4"));

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuser4","password":"wrong"}
                """))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"testuser4","password":"pass123"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser4"));
    }

    @Test
    void accessMeWithoutAuth_thenUnauthorized31() throws Exception {
        mvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

}
