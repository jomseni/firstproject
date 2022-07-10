package com.example.firstproject.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChefTest {

    @Autowired
    IngredientFactory ingredientFactory;   //IngredientFactory를 SpringBoot , ioc 컨테이너에서 가져올 수 있게 오토 와이어드를 해준다!
    @Autowired
    Chef chef;  // 이것을 등록만하면 오류가 난다, 이 chef가 ioc 컨테이너 등록이 안되어있으므로 DI를 해올 수 가 없다!!
    //Autowired 할 수 있고 DI 해올 수 있게 Commponent를 해줘야한다!

    @Test
    void 돈가스_요리하기(){
        //테스트는 4가지  순서로 나누어서 하면 편리하다!

        // 준비
//        IngredientFactory ingredientFactory = new IngredientFactory(); //식재료를 셰프가 하는게 아닌 , 조달 공장을 두어 의존성을 낮춘다! (변수를 두는 느낌!)
//        Chef chef = new Chef(ingredientFactory); //chef 객체 만들기!
        String menu = "돈가스";
        // 수행
        String food = chef.cook(menu); //chef가 요리를 했으면 좋겠다. 어떤 menu를 가지고 만들어 ==> food , test에 사용되는 셰프의 메서드 cook 은 chef 클래스에 메서드를 정의해준후 test에 사용하는 것이다.
        // 예상
        String expected = "한돈 등심으로 만든 돈가스";
        // 검증
        assertEquals(expected,food);        //객체를 문자열로 바꾸는게 아니므로 .toString()을 사용 하지않는다!
        System.out.println(food);  //test에서는 log를 쓸 수 없나봐..

    }

    @Test
    void 스테이크_요리하기(){
        //테스트는 4가지  순서로 나누어서 하면 편리하다!

        // 준비
//        IngredientFactory ingredientFactory = new IngredientFactory(); //식재료를 셰프가 하는게 아닌 , 조달 공장을 두어 의존성을 낮춘다! (변수를 두는 느낌!)
//        Chef chef = new Chef(ingredientFactory); //셰프가 식재료를 직접 구하는게 아니라 조달 공장에서 ingredientFactory를 만들어! 그것을 입력변수로 생각!!!그 재료를 받은 객체 가 chef
        String menu = "스테이크";
        // 수행
        String food = chef.cook(menu);
        // 예상
        String expected = "한우 꽃등심으로 만든 스테이크";
        // 검증
        assertEquals(expected,food);
        System.out.println(food);
    }
    @Test
    void 크리스피_치킨_요리하기(){

        // 준비
//        IngredientFactory ingredientFactory = new IngredientFactory();
//        Chef chef = new Chef(ingredientFactory);
        String menu= "크리스피 치킨";

        // 수행
        String food = chef.cook(menu);
        // 예상
        String expected = "국내산 10호 닭으로 만든 크리스피 치킨";
        // 검증
        assertEquals(expected, food);
        System.out.println(food);
    }
        //결과 적으로 돈가스와 스테이크 메뉴가 바뀌더라도 코드 변경없이(메뉴 바뀔떄마다 바꿔줬던 불편함) 요리가 가능하다!!!! 재료공장 객체을 만들어주면서 공장에서의 재료 자체를 객체로 만든다!
}