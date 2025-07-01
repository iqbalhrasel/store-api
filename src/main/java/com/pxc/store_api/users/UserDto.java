package com.pxc.store_api.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
//    @JsonIgnore
//    @JsonProperty("user_id")
    private Long id;
    private String name;
    private String email;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String phone;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
}
