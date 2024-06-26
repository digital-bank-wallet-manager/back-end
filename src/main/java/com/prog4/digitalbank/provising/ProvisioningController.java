package com.prog4.digitalbank.provising;

import com.prog4.digitalbank.Messages;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class ProvisioningController {
    private ProvisingServices provisingServices;
    @PostMapping( "/provising/save/{subCategoryId}")
    public Messages addNewProvisioning (@RequestBody Provisioning provisioning , @PathVariable int subCategoryId) throws SQLException {
        return provisingServices.saveProvising(provisioning , subCategoryId);
    }
}
