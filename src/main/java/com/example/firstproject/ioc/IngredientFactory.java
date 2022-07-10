package com.example.firstproject.ioc;

import org.springframework.stereotype.Component;

@Component // 해당 클래스르를 객체를 만들고, 이를 IoC 컨테이너에 등록!
public class IngredientFactory { // 여기 재료공장에서 소고기,돼지고기를 보내주는것
    public Ingredient get(String menu) {  //Ingredient 타입을 반환한다
        switch(menu){   //메뉴가 들어오면
            case "돈가스" :
                return  new Pork("한돈 등심");
            case "스테이크" :
                return new Beef("한우 꽃등심");
            case "크리스피 치킨" :
                return new Chicken("국내산 10호 닭");
            default:
                return null;

        }
    }

}
