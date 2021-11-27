package com.epam.training.ticketservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Account {

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private Boolean admin = false;

}
