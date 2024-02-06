package de.rieckpil;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebConnection;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(AdminController.class)
class AdminControllerMockMvcHtmlUnitTest {


  @Autowired
  private MockMvc mockMvc;
  private WebClient htmlUnitWebClient;

  @BeforeEach
  public void setup() {
    htmlUnitWebClient = new WebClient();
    htmlUnitWebClient.setWebConnection(new MockMvcWebConnection(mockMvc, htmlUnitWebClient));
  }

  @Test
  void shouldReturnAdminView() throws Exception {
    HtmlPage page = htmlUnitWebClient.getPage("/admin");
    DomElement secretMessageElement = page.getElementById("secret-message");
    String visibleText = secretMessageElement.getVisibleText();
    assertEquals("Kirill Lukyanov", visibleText);
  }
}