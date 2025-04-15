package com.example.itemservice.item.web.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.itemservice.item.domain.item.Item;
import com.example.itemservice.item.domain.item.ItemRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/basic/items")
//@RequiredArgsConstructor // final이 붙은 변수를 가지고 생성자를 알아서 만들어주기 때문에 생성자 생략 가능
public class BasicItemController {

    // final인 경우에는 생성자 주입이 필요하다
    // 필드 주입 불가!
    private final ItemRepository itemRepository;

    // 근데 사실 @Controller, @Service, @Repository 같은 컴포넌트 클래스에는 생성자가 하나뿐이면 자동으로
    // @Autowired 없이도 주입해줌
    @Autowired // 따라서 생략 가능
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        model.addAttribute("item", itemRepository.findById(itemId));
        return "basic/item";
    }

    // 등록 폼을 보여만 주는 화면
    @GetMapping("/add")
    public String add() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@RequestParam("itemName") String itemName, 
//                                    @RequestParam("price") int price, 
//                                    @RequestParam("quantity") int quantity,
//                                    Model model) {
//
//        Item newItem = new Item(itemName, price, quantity);
//        itemRepository.save(newItem);
//
//        model.addAttribute("item", newItem); // basic/item에 보여줄 화면
//        return "basic/item";
//    }
    
//    /** 
//     * @ModelAttribute("변수 이름")를 사용한 버전 (변수 이름 주의!!!)
//     */
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item,
//                                    Model model) {
//
//        Item newItem = itemRepository.save(item);
//
//        // @ModelAttribute는 모델에 알아서 해당 변수를 넣어줌 -> 따라서 생략 가능
//        // 이름은 @ModelAttribute에서 지정한 변수 이름으로 들어감
////        model.addAttribute("item", newItem); 
//        return "basic/item";
//    }
    
//    /** 
//     * @ModelAttribute("변수 이름")를 사용한 버전 (변수 이름 주의!!!)
//     * 변수 이름 생략 가능 -> 클래스 명의 첫 글자만 소문자로 바꾼게 이름이 된다
//     * ex) Item -> item
//     */
//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item, Model model) {
//
//        Item newItem = itemRepository.save(item);
//
//        // @ModelAttribute는 모델에 알아서 해당 변수를 넣어줌 -> 따라서 생략 가능
//        // 이름은 @ModelAttribute에서 지정한 변수 이름으로 들어감
////        model.addAttribute("item", newItem); 
//        return "basic/item";
//    }
    
//    /** 
//     * @ModelAttribute 생략 가능
//     * 사실 Model model 파라미터도 생략 가능
//     */
//    @PostMapping("/add")
//    public String addItemV4(Item item) {
//        itemRepository.save(item);
//        return "basic/item";
//    }
    
//    /**
//     * redirect를 적용해서 새로 고침 할 때 중복 추가가 되지 않게 함
//     */
//    @PostMapping("/add")
//    public String addItemV5(Item item) {
//        log.info("저장 전 아이템 아이디: " + item.getId());
//        itemRepository.save(item);
//        log.info("저장 후 아이템 아이디: " + item.getId());
//        return "redirect:/basic/items/" + item.getId();
//    }
    
    /**
     * redirect를 적용해서 새로 고침 할 때 중복 추가가 되지 않게 함
     * RedirectAttributes로 값을 추가해줌
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        
        // redirect 주소의 itemId로 바로 들어갈 수 있음
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        
        return "redirect:/basic/items/{itemId}" ;
    }
    
    // 수정 폼을 보여만 주는 화면
    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") long itemId, Model model) {
        model.addAttribute("item", itemRepository.findById(itemId));
        return "basic/editForm";
    }

//    @PostMapping("{itemId}/edit")
//    public String editItemV1(@PathVariable("itemId") long itemId,
//                                    @RequestParam("itemName") String itemName, 
//                                    @RequestParam("price") int price, 
//                                    @RequestParam("quantity") int quantity,
//                                    Model model) {
//
//        Item item = itemRepository.findById(itemId);
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//        
//        itemRepository.update(itemId, item);
//
//        model.addAttribute("item", item); // basic/item에 보여줄 화면
//        return "redirect:/basic/items/{itemId}";
//    }
    
//    @PostMapping("{itemId}/edit")
//    public String editItemV2(@PathVariable("itemId") long itemId,
//                                    @ModelAttribute("item") Item item, Model model) {
//
//        itemRepository.update(itemId, item);
//
////        model.addAttribute("item", item); // basic/item에 보여줄 화면
//        return "redirect:/basic/items/{itemId}";
//    }
    
    @PostMapping("{itemId}/edit")
    public String editItemV3(@PathVariable("itemId") long itemId, Item item) {

        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("ItemAA", 10000, 10));
        itemRepository.save(new Item("ItemBB", 20000, 30));
    }

}
