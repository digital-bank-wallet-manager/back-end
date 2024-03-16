package com.prog4.digitalbank.provising;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class ProvisingController {
    private ProvisingServices provisingServices;
    @PostMapping("/provising/save")
    public Provisioning addNewProvising (@RequestBody Provisioning provisioning) throws SQLException {
        return provisingServices.saveProvising(provisioning);
    }
}
