package com.grandlynn.spa.catalogue.service;


import com.grandlynn.spa.catalogue.entity.SysAdminUser;

public interface KmsService {
   Boolean sm3HmacAllUser();

   String sm3HmacByUser(Long userId);

   boolean authorizeSm3Hmac(SysAdminUser id);
}
