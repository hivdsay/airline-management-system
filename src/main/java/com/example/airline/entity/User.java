package com.example.airline.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//ID veritabanında üretiliyor. Hibernate burada ID’yi kayıt sonrası öğrenir.
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //Bu kullanıcı verisi çekildiğinde, rolleri de hemen aynı anda yükle.(eager)
    @ElementCollection(fetch = FetchType.EAGER) //→ Entity olmayan değerleri koleksiyon olarak saklamak için kullanılır
    @CollectionTable(name= "user_roles", joinColumns = @JoinColumn(name = "user_id")) //Koleksiyon için ayrı tablo oluşturmak ve ana tabloyla ilişkilendirmek
    @Column(name = "roles")                          //Ana tabloya foreign key ile bağlamak
    private Set<String> roles; //Tekrarsız, sırasız koleksiyon, duplicate yok bu yüzden List kullanmadık.


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){ //Spring Security’de bir yetkiyi temsil eden interface.
        return roles.stream() //Java Stream API ile koleksiyon üzerinde işlem yapmayı sağlar. Bu sayede her rolü map edip dönüştürebiliriz
                .map(SimpleGrantedAuthority::new) //Rol String’ini ("ROLE_USER") SimpleGrantedAuthority objesi’ne çeviriyor
                .collect(Collectors.toSet());     //Yani "ROLE_USER" → new SimpleGrantedAuthority("ROLE_USER").Security bu objeleri rol yetkisi olarak tanıyor.
    }            //Stream’den koleksiyon oluşturmak için kullanılır. Collectors.toSet() → Stream’i Set’e dönüştürür
                //Yani duplicate roller varsa tekilleştirir (mantıklı çünkü bir kullanıcı aynı rolü birden fazla kez alamaz)

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
