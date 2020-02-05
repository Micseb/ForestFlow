/**
 * Copyright 2020 DreamWorks Animation L.L.C.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dreamworks.forestflow.serving.interfaces

import com.dreamworks.forestflow.serving.impl.LocalFileArtifactReader
import com.dreamworks.forestflow.utils.SourceStorageProtocols

// INFO: Why do we even create an instance of this class? So we can utilize the same calling patter without switch statements
abstract class ArtifactReader() {
  /**
    *
    * @param artifactName
    *                     Name of the artifact this reader is used to load
    * @param localDirAbsolutePath
    *                             All artifact readers are provided with the local path of the downloaded content.
    *                             They make their own decision on if they need to use it
    * @return
    */
  def getArtifact(artifactName: String, localDirAbsolutePath: String): Array[Byte]
  val providedBasePath: String
}

object ArtifactReader {
  def getArtifactReader(providedBasePath: String) : ArtifactReader = {
    val protocol = SourceStorageProtocols.getProtocolWithDefault(providedBasePath, SourceStorageProtocols.LOCAL)
    protocol match {
      case SourceStorageProtocols.LOCAL => LocalFileArtifactReader(providedBasePath)
      //case Protocols.HTTP => HTTPArtifactReader()
      case _ => LocalFileArtifactReader(providedBasePath)
    }

  }
}