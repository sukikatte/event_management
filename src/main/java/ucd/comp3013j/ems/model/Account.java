
package ucd.comp3013j.ems.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass @Data @NoArgsConstructor
public abstract class Account {
    @GeneratedValue @Id
    private long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    @Setter
    private Role role;

    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = Role.USER;
        // 多个role都能登录，getRole应返回role的大写文字s
    }
    public void setRole(Role role) {this.role = role;}
}
