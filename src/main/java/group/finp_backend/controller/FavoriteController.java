package group.finp_backend.controller;

import group.finp_backend.dto.favorite.FavoriteDto;
import group.finp_backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public FavoriteDto addFavorite(@RequestParam Long userId, @RequestParam Long postId){
        return favoriteService.addFavorite(userId, postId);
    }

    @DeleteMapping
    public void removeFavorite(@RequestParam Long userId, @RequestParam Long postId){
        favoriteService.removeFavorite(userId, postId);
    }

    @GetMapping("/user/{userId}")
    public List<FavoriteDto> getFavoritesByUserId(@PathVariable Long userId){
        return favoriteService.getFavoritesByUserId(userId);
    }

}
