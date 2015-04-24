/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.mycompany.backend;

import java.io.IOException;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
//    GcsService bucket;



    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("https://00e9e64bacefa7027ff026b372da6d96b546de7ad668167fc0-apidata.googleusercontent.com/download/storage/v1_internal/b/autoplay_audio/o/09%20Holding%20on%20to%20Heaven.mp3?qk=AD5uMEvj2eCVY-lf5qMYgI2zt6KKUCBGZKEUpnnuLE6EZVbgHKWVoEECuWd-ss7KnYa61JCvv_DCF4FkJFowdHmoG0acAES5amjyFvkdNRq7r2pP3gdxl6x0fREEsMEqGvzI5SfVup2r-fCft_AAuuvW5oaii_lmGRZoxIhH3EejvsfYGHO4-K_Cl0xwwerCM5YdzB7f3G29_-v_YjdL9urFc_eS5PxRd9ZolUze69skDFV7wztdPQxvu6mnU1VW-597if-J3HhLueftY3-CyOmUsW7kqp2d2bv0vTfNy995VoawoNvPsWHjkLO3jtclTRoQrHTkdL7N56iur2f5V4n6m7Om-CaxIeKIDMe5TVtWdvP9fIUhXl5NyzuwvqRzeYgM5psX8lHs5b3yMifbv1bHXBJDonIGG07i_nWIR4kBruNWbRNgIUQ0xfwqZVV-oVkhtR71KdeEQa9f7BOPnzd8KC8o-LJHcHt_49F52gdpDs4zzUIql7XatTUBmk-f61wCyI-D68FO4Vy0Rh6IvcQz_-XN1rpj7M-w02FPwV6td-SIOQwyjWhWQpSeInPi6BXfWdEQYevG8BWB4Bt0bq7OokJySFgN-LAEFeh70o7-BacVYU-ad04pD4qIPpC8K-zxvH10EboaoNX1RX3u1eEqSYqL6OLsTqxWsPIaO1cYxIjH6DSz-WwVcp_iWlc5ZVEe7AtCTX3Jv6Y4n1JbraUhUWsnnJCXig1igiuMhnTrRWH4VSrHybY");
    }
}
