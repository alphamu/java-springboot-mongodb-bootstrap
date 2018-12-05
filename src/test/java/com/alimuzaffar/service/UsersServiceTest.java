package com.alimuzaffar.service;

import com.alimuzaffar.domain.User;
import com.alimuzaffar.domain.UserRepository;
import com.alimuzaffar.model.Filter;
import org.bson.internal.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.never;

@RunWith(SpringRunner.class)
public class UsersServiceTest {
    @MockBean
    UserRepository mockRepository;

    @Autowired
    ApplicationContext context;

    @Test
    public void test_userService_getUser() {
        User user = new User();
        user.setId("123");
        user.setDocument_id("123");
        Mockito.when(mockRepository.findByUserId("123")).thenReturn(user);
        Mockito.when(mockRepository.findByUserId("")).thenReturn(user); // validation should still return null
        UserRepository userRepoFromContext = context.getBean(UserRepository.class);
        UsersService service = new UsersService(userRepoFromContext);
        User resp = service.getUser("123");
        User respEmpty = service.getUser("");

        Assert.assertEquals("123", resp.getId());
        // If validation works, this should be null
        Assert.assertNull(respEmpty);
        Mockito.verify(mockRepository).findByUserId("123");
        Mockito.verify(mockRepository, never()).findByUserId("");
    }

    @Test
    public void test_json_decoding_from_base64() {
        String original = "{ \"attribute\": \"_id\", \"operator\": \"eq\", \"value\": \"123\" }";
        String base64UrlEscaped = "eyAiYXR0cmlidXRlIjogIl9pZCIsICJvcGVyYXRvciI6ICJlcSIsICJ2YWx1ZSI6ICIxMjMiIH0%3D";
        try {
            String base64UrlDecoded = URLDecoder.decode(base64UrlEscaped, "UTF-8");
            String base64Decoded = new String(Base64.decode(base64UrlDecoded));
            Assert.assertEquals(original, base64Decoded);

            Filter f = Filter.fromJsonBase64String(base64UrlDecoded);
            Assert.assertNotNull(f);
            Assert.assertEquals(f.getAttribute(), "document_id");
            Assert.assertEquals(f.getValue(), "123");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertNull(e.getMessage(), e);
        }
    }

    @Test
    public void test_search_filter_validation() {
        String original = "{ \"attribute\": \"_id\", \"operator\": \"eq\", \"value\": \"123\" }";
        String original2 = "{ \"attribute\": \"_id\", \"operator\": \"eq\", \"range\": {} }";
        try {
            String o1 = URLEncoder.encode(Base64.encode(original.getBytes()), "UTF-8");
            String o2 = URLEncoder.encode(Base64.encode(original2.getBytes()), "UTF-8");
            List<String> array = Arrays.asList(o1, o2);
            List<Filter> filters = Filter.fromJsonBase64String(array);
            Assert.assertNull("This should throw a validation exception", filters);
        } catch (Exception e) {
            Assert.assertNotNull(e.getMessage(), e);
        }
    }

    @Test
    public void test_search_filter_validation_range_lte() {
        String original = "{ \"attribute\": \"_id\", \"operator\": \"lte\", \"range\": {} }";
        try {
            String o1 = URLEncoder.encode(Base64.encode(original.getBytes()), "UTF-8");
            List<String> array = Arrays.asList(o1);
            List<Filter> filters = Filter.fromJsonBase64String(array);
            Assert.assertNull("This should throw a validation exception", filters);
        } catch (Exception e) {
            Assert.assertNotNull(e.getMessage(), e);
        }
    }

    @Test
    public void test_search_filter_validation_range_gte() {
        String original = "{ \"attribute\": \"_id\", \"operator\": \"gte\", \"range\": {} }";
        try {
            String o1 = URLEncoder.encode(Base64.encode(original.getBytes()), "UTF-8");
            List<String> array = Arrays.asList(o1);
            List<Filter> filters = Filter.fromJsonBase64String(array);
            Assert.assertNull("This should throw a validation exception", filters);
        } catch (Exception e) {
            Assert.assertNotNull(e.getMessage(), e);
        }
    }

    @Test
    public void test_search_filter_validation_range_eq() {
        String original = "{ \"attribute\": \"_id\", \"operator\": \"eq\", \"range\": {}}";
        try {
            String o1 = URLEncoder.encode(Base64.encode(original.getBytes()), "UTF-8");
            List<String> array = Arrays.asList(o1);
            List<Filter> filters = Filter.fromJsonBase64String(array);
            Assert.assertNotNull("eq is allowed with range", filters);
        } catch (Exception e) {
            Assert.assertNull(e.getMessage(), e);
        }
    }
}
