{-# LANGUAGE CPP #-}
{-# LANGUAGE NoRebindableSyntax #-}
#if __GLASGOW_HASKELL__ >= 810
{-# OPTIONS_GHC -Wno-prepositive-qualified-module #-}
#endif
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
{-# OPTIONS_GHC -w #-}
module Paths_containers (
    version,
    getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where


import qualified Control.Exception as Exception
import qualified Data.List as List
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude


#if defined(VERSION_base)

#if MIN_VERSION_base(4,0,0)
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#else
catchIO :: IO a -> (Exception.Exception -> IO a) -> IO a
#endif

#else
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#endif
catchIO = Exception.catch

version :: Version
version = Version [0,6,8] []

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir `joinFileName` name)

getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath




bindir, libdir, dynlibdir, datadir, libexecdir, sysconfdir :: FilePath
bindir     = "/home/mateo/.cabal/store/ghc-9.4.8/containers-0.6.8-378ab146e205d20ce53ab6cf10c38bda458ae2252e90bbd8cf5a8f103455517f/bin"
libdir     = "/home/mateo/.cabal/store/ghc-9.4.8/containers-0.6.8-378ab146e205d20ce53ab6cf10c38bda458ae2252e90bbd8cf5a8f103455517f/lib"
dynlibdir  = "/home/mateo/.cabal/store/ghc-9.4.8/containers-0.6.8-378ab146e205d20ce53ab6cf10c38bda458ae2252e90bbd8cf5a8f103455517f/lib"
datadir    = "/home/mateo/.cabal/store/ghc-9.4.8/containers-0.6.8-378ab146e205d20ce53ab6cf10c38bda458ae2252e90bbd8cf5a8f103455517f/share"
libexecdir = "/home/mateo/.cabal/store/ghc-9.4.8/containers-0.6.8-378ab146e205d20ce53ab6cf10c38bda458ae2252e90bbd8cf5a8f103455517f/libexec"
sysconfdir = "/home/mateo/.cabal/store/ghc-9.4.8/containers-0.6.8-378ab146e205d20ce53ab6cf10c38bda458ae2252e90bbd8cf5a8f103455517f/etc"

getBinDir     = catchIO (getEnv "containers_bindir")     (\_ -> return bindir)
getLibDir     = catchIO (getEnv "containers_libdir")     (\_ -> return libdir)
getDynLibDir  = catchIO (getEnv "containers_dynlibdir")  (\_ -> return dynlibdir)
getDataDir    = catchIO (getEnv "containers_datadir")    (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "containers_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "containers_sysconfdir") (\_ -> return sysconfdir)



joinFileName :: String -> String -> FilePath
joinFileName ""  fname = fname
joinFileName "." fname = fname
joinFileName dir ""    = dir
joinFileName dir fname
  | isPathSeparator (List.last dir) = dir ++ fname
  | otherwise                       = dir ++ pathSeparator : fname

pathSeparator :: Char
pathSeparator = '/'

isPathSeparator :: Char -> Bool
isPathSeparator c = c == '/'
