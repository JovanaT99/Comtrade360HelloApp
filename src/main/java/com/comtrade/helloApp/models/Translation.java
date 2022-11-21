package com.comtrade.helloApp.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
public class Translation {

@Id
    private String language;
    private String translation;
}
