module Main (main) where
import Test.HUnit
import qualified System.Exit as Exit
import System.Exit (exitFailure, exitSuccess)
import Control.Monad (when)
import Pred 
import Dibujo


-- predicado :: a -> Bool
-- (==  )

testCambia1 :: Test
testCambia1 = TestCase (assertEqual "Test with predicate True" "Figura 10" (show $ cambia (\x -> figura (x+1)) (>5) 9))

-- Define test cases for Orr
testOrr1 = TestCase (assertEqual "test_orr with True and True" True (orr 0.0 0.0 True True))
testOrr2 = TestCase (assertEqual "test_orr with True and False" True (orr 0.0 0.0 True False))
testOrr3 = TestCase (assertEqual "test_orr with False and True" True (orr 0.0 0.0 False True))
testOrr4 = TestCase (assertEqual "test_orr with False and False" False (orr 0.0 0.0 False False))

-- Define test cases for Andd
testAnd1 = TestCase (assertEqual "Test with True and True" True (andd 0.0 0.0 True True))
testAnd2 = TestCase (assertEqual "Test with True and False" False (andd 0.0 0.0 True False))
testAnd3 = TestCase (assertEqual "Test with False and True" False (andd 0.0 0.0 False True))
testAnd4 = TestCase (assertEqual "Test with False and False" False (andd 0.0 0.0 False False))

testCambiar1 :: Test
testCambiar1 = TestCase $ assertEqual "test_cambiar" "Encimar (Rotar (Figura 11)) (Figura 5)" (show $ cambiar (>5) (\x -> figura (x+1)) (encimar (rotar (figura 10)) (figura 5)))

testCambiar2 :: Test
testCambiar2 = TestCase $ assertEqual "test_cambiar" "Encimar (Rotar (Figura 10)) (Rotar (Figura 5))" (show $ cambiar (<=5) (rotar . figura) (encimar (rotar (figura 10)) (figura 5)))

testCambiar3 :: Test
testCambiar3 = TestCase $ assertEqual "test_cambiar" "Encimar (Rotar (Rotar (Figura 10))) (Figura 5)" (show $ cambiar (==10) (rotar . figura) (encimar (rotar (figura 10)) (figura 5)))

testanyDib1 :: Test
testanyDib1 = TestCase $ assertEqual "test_anydib" "False" (show $ anyDib (==8) (juntar 1 1 (figura 10) (figura 7)))

testanyDib2 :: Test
testanyDib2 = TestCase $ assertEqual "test_anydib" "True" (show $ anyDib (==10) (juntar 1 1 (figura 10) (figura 7)))

testanyDib3 :: Test
testanyDib3 = TestCase $ assertEqual "test_anydib" "True" (show $ anyDib ((== 0) . (`mod` 2)) (juntar 1 1 (figura 10) (figura 7)))

testallDib1 :: Test
testallDib1 = TestCase $ assertEqual "test_allDib" "False" (show $ allDib ((== 0) . (`mod` 2)) (juntar 1 1 (figura 10) (figura 7)))

testallDib2 :: Test
testallDib2 = TestCase $ assertEqual "test_allDib" "True" (show $ allDib ((== 0) . (`mod` 3)) (juntar 1 1 (figura 33) (figura 15)))

testallDib3 :: Test
testallDib3 = TestCase $ assertEqual "test_allDib" "False" (show $ allDib ((== 14) . (* 2)) (juntar 1 1 (figura 10) (figura 7)))

--testandP :: Test
--testandP = TestCase $ assertEqual "test_andP" "False" (show $ andP --figura 10)

testandP1 = TestCase (assertEqual "andP with both predicates True" True (andP (> 5) (< 10) 7))
testandP2 = TestCase (assertEqual "andP with one predicate False" False (andP (> 5) (< 10) 12))
testandP3 = TestCase (assertEqual "andP with both predicates False" False (andP (> 5) (< 10) 3))

testorP1 = TestCase (assertEqual "orP with both predicates True" True (orP (> 5) (< 10) 7))
testorP2 = TestCase (assertEqual "orP with one predicate True" True (orP (> 5) (< 10) 12))
testorP3 = TestCase (assertEqual "orP with both predicates False" False (orP (> 5) (< 2) 3))

-- ARREGLO CON TODOS LOS CASOS A TESTEAR

tests = TestList [testOrr1, testOrr2, testOrr3, testOrr4, testAnd1, testAnd2, testAnd3, testAnd4, testCambia1, testCambiar1, testCambiar2, testCambiar3, testanyDib1, testanyDib2, testanyDib3, testallDib1, testallDib2, testallDib3, testandP1, testandP2, testandP3, testorP1, testorP2, testorP3]

main :: IO ()
main = do
    --putStrLn "Test suite not yet implemented."
    --when falla exitFailure

    putStrLn "Running tests..."
    result <- runTestTT tests
    when (failures result > 0) $ do Exit.exitFailure
    when (failures result <= 0) $ do Exit.exitSuccess