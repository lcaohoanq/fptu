package com.orchid.orchidbe.domain.role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    public enum RoleName {
        ADMIN, USER, MANAGER, STAFF
    }

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    public RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }

}
