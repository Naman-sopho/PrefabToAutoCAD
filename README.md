## PrefabToAutoCAD
Transforms the JSON representation in a StructureTemplate prefab into a .dxf file that can be imported into AutoCAD.

#### Usage
This currently works only if the prefab consists of just the `regionsToFill` list, everything else has to be deleted from the 
prefab file. Provide the name of this file as an argument and the required `output.dxf` is created.
