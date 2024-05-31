package group.finp_backend.controller;

import group.finp_backend.dto.FavoriteDto;
import group.finp_backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.addFavorite(favoriteDto);
        return ResponseEntity.ok().body("Favorite added successfully");
    }

    @DeleteMapping
    public ResponseEntity<?> removeFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.removeFavorite(favoriteDto);
        return ResponseEntity.ok().body("Favorite removed successfully");
    }
}
