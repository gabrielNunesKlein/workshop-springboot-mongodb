package com.gnk.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.gnk.workshopmongo.domain.Post;
import com.gnk.workshopmongo.domain.User;
import com.gnk.workshopmongo.dto.CommentDTO;
import com.gnk.workshopmongo.dto.authorDTO;
import com.gnk.workshopmongo.repository.PostRepository;
import com.gnk.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post p1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!" , new authorDTO(maria));
		Post p2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz !" , new authorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new authorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("22/03/2018"), new authorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new authorDTO(alex));
		
		p1.getComents().addAll(Arrays.asList(c1, c2));
		p2.getComents().add(c3);
		
		postRepository.saveAll(Arrays.asList(p1, p2));
		
		maria.getPosts().addAll(Arrays.asList(p1, p2));
		userRepository.save(maria);
	}

}
