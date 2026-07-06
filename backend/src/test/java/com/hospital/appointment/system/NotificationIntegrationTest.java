package com.hospital.appointment.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// Security test utilities to handle CSRF tokens
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationIntegrationTest {

    @Autowired
    private MockMvc mockMvc; 

    @Test
    @WithMockUser(username = "admin_test_user", roles = {"ADMIN"}) 
    @SuppressWarnings("null")
    public void testAppointmentLifecycleNotificationWithSecurityActive() throws Exception {
        String testPayload = """
            {
              "appointmentId": 120,
              "patientId": 450,
              "email": "pasindupramodaya0@gmail.com",
              "phone": "+94763372067",
              "status": "APPROVED",
              "date": "2026-07-05",
              "time": "09:30 AM"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/test/notifications/lifecycle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testPayload)
                .with(csrf())) 
                .andExpect(MockMvcResultMatchers.status().isOk()); 
    }

    @Test
    @WithMockUser(username = "doctor_test_user", roles = {"DOCTOR"}) 
    @SuppressWarnings("null")
    public void testQueueProximityReminderNotification() throws Exception {
        // Payload configured with keys extracted from your controller request mappings
        String queuePayload = """
            {
              "currentServingToken": 10,
              "patientTargetToken": 12,
              "phone": "+94763372067",
              "appointmentId": 121,
              "patientId": 455
            }
        """;

        // FIXED: Pointing directly to /queue-step as configured in your controller
        mockMvc.perform(MockMvcRequestBuilders.post("/api/test/notifications/queue-step")
                .contentType(MediaType.APPLICATION_JSON)
                .content(queuePayload)
                .with(csrf())) 
                .andExpect(MockMvcResultMatchers.status().isOk()); 
    }
}