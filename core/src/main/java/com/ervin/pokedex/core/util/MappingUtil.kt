package com.ervin.pokedex.core.util

import com.ervin.pokedex.core.data.source.local.entity.ElementEntity
import com.ervin.pokedex.core.data.source.local.entity.PokemonEntity
import com.ervin.pokedex.core.data.source.local.entity.foreignkey.PokemonElementEntity
import com.ervin.pokedex.core.data.source.local.response.PokemonLocalResponse
import com.ervin.pokedex.core.data.source.remote.response.ListElementApiResponse
import com.ervin.pokedex.core.data.source.remote.response.PokemonResponse
import com.ervin.pokedex.core.domain.model.Pokemon
import com.ervin.pokedex.core.domain.model.Type
import java.util.Locale

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

fun mappingPokemonResponseToPokemonElementEntity(pokemon: PokemonResponse) =
    pokemon.pokemonTypes.map {
        PokemonElementEntity(pokemon.pokemonId, it.type.url.split("/")[6].toInt())
    }

fun mappingPokemonEntityToDomainModel(listPokemon: List<PokemonLocalResponse>): List<Pokemon> =
    listPokemon.map { pokemon ->
        Pokemon(
            pokemonId = pokemon.pokemonEntity.pokemonId,
            pokemonName = pokemon.pokemonEntity.pokemonName.capitalize(Locale.getDefault()),
            pokemonSpritesUrl = pokemon.pokemonEntity.pokemonSpritesUrl,
            pokemonAttack = pokemon.pokemonEntity.attack,
            pokemonDefense = pokemon.pokemonEntity.defense,
            pokemonSpAtk = pokemon.pokemonEntity.specialAttack,
            pokemonSpDef = pokemon.pokemonEntity.specialDefense,
            isFavorite = pokemon.pokemonEntity.isFavorite,
            listType = mappingElementEntityToDomainModel(pokemon.typeList),
            pokemonHeight = pokemon.pokemonEntity.height,
            pokemonHp = pokemon.pokemonEntity.hp,
            pokemonSpeed = pokemon.pokemonEntity.speed,
            pokemonWeight = pokemon.pokemonEntity.weight
        )
    }

fun mappingElementEntityToDomainModel(listType: List<ElementEntity>) =
    listType.map {
        Type(
            typeId = it.typeId,
            typeColor = it.typeColor,
            typeName = it.typeName
        )
    }

fun mappingElementApiResponseToLocalResponse(element: ListElementApiResponse.Result) =
    ElementEntity(
        element.id,
        element.name,
        when (element.name) {
            "psychic" -> "#F95587"
            "normal" -> "#A8A77A"
            "fighting" -> "#C22E28"
            "flying" -> "#A98FF3"
            "poison" -> "#A33EA1"
            "ground" -> "#E2BF65"
            "rock" -> "#B6A136"
            "ghost" -> "#735797"
            "steel" -> "#B7B7CE"
            "bug" -> "#A6B91A"
            "fire" -> "#EE8130"
            "water" -> "#6390F0"
            "grass" -> "#7AC74C"
            "electric" -> "#F7D02C"
            "ice" -> "#96D9D6"
            "dragon" -> "#6F35FC"
            "dark" -> "#705746"
            "fairy" -> "#D685AD"
            else -> "#"
        }
    )

fun Pokemon.mappingPokemonDomainModelToEntity() =
    PokemonEntity(
        pokemonId,
        pokemonName,
        pokemonSpritesUrl,
        pokemonSpeed,
        pokemonSpDef,
        pokemonSpAtk,
        pokemonDefense,
        pokemonAttack,
        pokemonHp,
        pokemonWeight,
        pokemonHeight,
        isFavorite
    )