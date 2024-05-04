package com.jimmysalazar.springjdbc.models;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer id;
    @NonNull
    private String street;
    @NonNull
    private String number;
    @NonNull
    private Integer pc;
    @NonNull
    private Integer employeeId;
}
