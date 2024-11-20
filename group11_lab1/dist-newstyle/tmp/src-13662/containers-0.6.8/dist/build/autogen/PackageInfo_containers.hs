{-# LANGUAGE NoRebindableSyntax #-}
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
{-# OPTIONS_GHC -w #-}
module PackageInfo_containers (
    name,
    version,
    synopsis,
    copyright,
    homepage,
  ) where

import Data.Version (Version(..))
import Prelude

name :: String
name = "containers"
version :: Version
version = Version [0,6,8] []

synopsis :: String
synopsis = "Assorted concrete container types"
copyright :: String
copyright = ""
homepage :: String
homepage = ""
