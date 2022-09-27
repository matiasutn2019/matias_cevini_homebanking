package com.mindhub.homebanking.common.mail;

public interface IEmail {

    String getEmailTo();
    String getSubject();
    IContent getContent();
}
