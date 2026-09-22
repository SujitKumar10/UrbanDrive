package com.Timepass.ecotrack.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails{
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	 
	    @Column(length = 50,nullable = false)
	    private String name;
	    
	    @Column(nullable = false,unique = true)
	    private String email;
	    
	    @Column(nullable = false)
	    private String password;

	    
	    
	    @ManyToOne(fetch = FetchType.EAGER)
	    private Role role;



		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			 return Arrays.asList(new SimpleGrantedAuthority(role.getRoleName().toString()));
		}



		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return email;
		}
		
		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return password;
		}



}