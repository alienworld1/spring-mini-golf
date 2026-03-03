package com.example.minigolf;

import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class MinigolfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinigolfApplication.class, args);
    }
}

@Entity
class Score {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String playerName;
    private int strokes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public int getStrokes() { return strokes; }
    public void setStrokes(int strokes) { this.strokes = strokes; }
}

interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findAllByOrderByStrokesAsc();
}

@RestController
@RequestMapping("/api/scores")
class ScoreController {
    // this is a simple controller to handle score submissions and retrievals
    private final ScoreRepository repository;

    public ScoreController(ScoreRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Score> getLeaderboard() {
        return repository.findAllByOrderByStrokesAsc();
    }

    @PostMapping
    public Score submitScore(@RequestBody Score score) {
        return repository.save(score);
    }
}