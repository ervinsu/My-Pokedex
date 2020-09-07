package com.ervin.pokedex.core.util

import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import com.ervin.pokedex.core.domain.model.Pokemon

fun mappingPokemonApiResponseToLocalResponse(pokemon: PokemonResponse) =
    PokemonEntity(
        pokemonId = pokemon.pokemonId,
        pokemonName = pokemon.pokemonName,
        pokemonSpritesUrl = pokemon.pokemonSprites.pokemonFrontSpriteUrl,
        hp = pokemon.pokemonBaseStats[0].statValue,
        attack = pokemon.pokemonBaseStats[1].statValue,
        defense = pokemon.pokemonBaseStats[2].statValue,
        specialAttack = pokemon.pokemonBaseStats[3].statValue,
        specialDefense = pokemon.pokemonBaseStats[4].statValue,
        speed = pokemon.pokemonBaseStats[5].statValue,
        height = pokemon.pokemonHeight,
        weight = pokemon.pokemonWeight
    )

fun mappingPokemonEntityToDomainModel(listPokemon: List<PokemonEntity>): List<Pokemon> =
    listPokemon.map { pokemon ->
        Pokemon(pokemonId = pokemon.pokemonId, pokemonName = pokemon.pokemonName)
    }