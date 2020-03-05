# MC Biome Map Server

A HTTP server for finding info on minecraft biomes. Runs using [Amidst](https://github.com/toolbox4minecraft/amidst).

## API

| uri            | Description                        | Parameters                                           |
| -------------  | -------------                      | -----                                                |
| /biome/seed    | Get biome information from a seed  | chunkStartX, chunkStartY, chunkEndX, chunkEndY, seed |
| /biome/save    | Get biome information from a save  | chunkStartX, chunkStartY, chunkEndX, chunkEndY, save |
| /biome/dispose | Dispose of a world, freeing memory | seed or save                                         |

## Command Line Arguments

```
[minecraft installation directory] [port]
```

