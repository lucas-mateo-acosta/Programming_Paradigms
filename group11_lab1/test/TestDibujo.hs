module Main (main) where
import Test.HUnit
import Control.Monad (when)
import qualified System.Exit as Exit
import Dibujo


data CuadradoTriangulo = Cuadrado | Triangulo deriving(Show,Eq)

testFigura :: Test
testFigura = TestCase $ assertEqual "test_figura" "Figura 10" (show $ figura 10)

testRotar :: Test
testRotar = TestCase $ assertEqual "test_rotar" "Rotar (Figura 1)" (show $ rotar $ figura 1)

testApilar :: Test
testApilar = TestCase $ assertEqual "test_apilar" "Apilar 1.0 1.0 (Figura 1) (Figura 2)" 
                                     (show $ figura 1 .-. figura 2)

testJuntar :: Test
testJuntar = TestCase $ assertEqual "test_juntar" "Juntar 1.0 1.0 (Figura 1) (Figura 2)" 
                                     (show $ figura 1 /// figura 2)
                                                                                                              
testEspejar :: Test
testEspejar = TestCase $ assertEqual "test_espejar" "Espejar (Figura 1)" (show $ espejar $ figura 1)


testCuarteto :: Test
testCuarteto = TestCase $ assertEqual "test_cuarteto" "Apilar 1.0 1.0 (Juntar 1.0 1.0 (Figura 1) (Figura 2)) (Juntar 1.0 1.0 (Figura 3) (Figura 4))"
                                                        (show $ cuarteto (figura 1) (figura 2) (figura 3) (figura 4) )

testR45 :: Test
testR45 = TestCase $ assertEqual "test_r45" "Rot45 (Figura 1)" (show $ rot45 $ figura 1)


testR180 :: Test
testR180 = TestCase $ assertEqual "test_r180" "Rotar (Rotar (Figura 1))" (show $ r180 $ figura 1)


testR270 :: Test
testR270 = TestCase $ assertEqual "test_r270" "Rotar (Rotar (Rotar (Figura 1)))" (show $ r270 (figura 1))

testEncimar :: Test
testEncimar = TestCase $ assertEqual "test_encimar" "Encimar (Figura 1) (Figura 2)" (show $ (figura 1) ^^^ (figura 2))
                 

testComp :: Test
testComp = TestCase $ assertEqual "test_comp" "Rotar (Rotar (Rotar (Figura 1)))" 
                                (show $ comp 3 rotar (figura 1))
                 

testEncimar4 :: Test
testEncimar4 =
    TestCase $ assertEqual "test_encimar4" "Encimar (Figura 1) (Encimar (Rotar (Figura 1)) (Encimar (Rotar (Rotar (Figura 1))) (Rotar (Rotar (Rotar (Figura 1))))))" 
                                        (show $ encimar4 $ figura 1) 

testCiclar :: Test
testCiclar = TestCase $ assertEqual "test_ciclar" "Apilar 1.0 1.0 (Juntar 1.0 1.0 (Figura 1) (Rotar (Figura 1))) (Juntar 1.0 1.0 (Rotar (Rotar (Figura 1))) (Rotar (Rotar (Rotar (Figura 1)))))" 
                                (show $ ciclar $ figura 1)

ejemploFigura1 :: Dibujo Int
ejemploFigura1 = juntar 1 1 (espejar (rotar (figura 1))) (figura 2)

ejemploFigura2 :: Dibujo CuadradoTriangulo
ejemploFigura2 = juntar 1 1 (espejar (rotar (figura Cuadrado))) (figura Cuadrado)

testMapDib :: Test
testMapDib = TestCase $ assertEqual "test_mapDib" "Juntar 1.0 1.0 (Espejar (Rotar (Figura 2))) (Figura 3)" 
                                    (show $ mapDib (\x -> x+1 ) ejemploFigura1)

testChange :: Test
testChange = TestCase $ assertEqual "test_change" "Juntar 1.0 1.0 (Espejar (Rotar (Figura Triangulo))) (Figura Triangulo)" 
                                    (show $ change (\x -> figura Triangulo) ejemploFigura2)

testFoldDib :: Test
testFoldDib = TestCase $ assertEqual "test_foldDib" "Juntar 1.0 1.0 (Espejar (Rotar (Figura Cuadrado))) (Figura Cuadrado)" 
                                    (show $ foldDib figura rotar espejar rot45 apilar juntar encimar ejemploFigura2)



tests :: Test
tests = TestList [TestLabel "test_figura" testFigura,
                  TestLabel "test_rotar" testRotar,  
                  TestLabel "test_apilar" testApilar,
                  TestLabel "test_juntar" testJuntar,
                  TestLabel "test_espejar" testEspejar,
                  TestLabel "test_cuarteto" testCuarteto,
                  TestLabel "test_r180" testR180,
                  TestLabel "test_270" testR270,
                  TestLabel "test_encimar" testEncimar,
                  TestLabel "test_comp" testComp,
                  TestLabel "test_encimar4" testEncimar4,
                  TestLabel "test_r45" testR45,
                  TestLabel "test_ciclar" testCiclar,
                  TestLabel "test_mapdib" testMapDib,
                  TestLabel "test_change" testChange,
                  TestLabel "test_foldDib" testFoldDib
                ]



main :: IO ()
main = do
    result <- runTestTT tests
    when ((failures result) > 0) $ do Exit.exitFailure
    when ((failures result) <= 0) $ do Exit.exitSuccess
    
    
