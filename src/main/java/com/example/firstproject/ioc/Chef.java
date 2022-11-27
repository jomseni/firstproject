package com.example.firstproject.ioc;

import org.springframework.stereotype.Component;

@Component  // Component를 하면 이 클래스를 가지고 IoC객체를 만들어준다, 그리고 자동적으로 관리를 하는 것이다. 외부에서 요청하면 Autowired를 하여 필요한곳에 삽입을 시켜주는 것이다! chef 메서드를!
// 이 과정이 DI해주는 과정이라고 생각하면 된다!
public class Chef {

    // 셰프는 식재료 공장을 알고 있음
    private IngredientFactory ingredientFactory;
    // 셰프가 식재료 공장과 협업하기 위한 DI, 외부에서  IngredientFactory ingredientFactory 공장 정보를 가져온다
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu) {  //요리사가 하는일!

        // 요리 재료 준비 , factory에서 식재료를 반환해주는데 menu에들어가는 것을 반환 해준다.
        Ingredient ingredient = ingredientFactory.get(menu); //메뉴에 상관없이 공장에서 그 재료를 반환 할 수 있게해준다 , Ingredient는 pork와 beef의 부모 클래스가 된다
        // 요리 반환

        return ingredient.getName() + "으로 만든 " +menu; //ingredient 객체의 상속 속성을 이용해 다양한 메뉴가 들어가도 ingredient를 이용해 그냥 자동 적용 가능하다
    }
}
