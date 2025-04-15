package com.example.itemservice.item.web.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.itemservice.item.domain.item.Item;
import com.example.itemservice.item.domain.item.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        // 해당 컨트롤러가 호출 될 때 내가 만든 검증기 적용
        dataBinder.addValidators(itemValidator);
    }
    
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        model.addAttribute("item", itemRepository.findById(itemId));
        return "validation/v2/item";
    }

    // 등록 폼을 보여만 주는 화면
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    /**
     * 
     * @param item
     * @param bindingResult
     * @param redirectAttributes
     * 
     * BindingResult bindingResult 파리미터의 위치는 @ModelAttribute Item item 다음에 와야 한다.
     * @return
     */
//        @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        // 검증 로직
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if(item.getQuantity() == null || item.getQuantity() < 0 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        // 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "총 금액은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);

        // redirect 주소의 itemId로 바로 들어갈 수 있음
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        // 조회 화면으로 리다이렉트
        return "redirect:/validation/v2/items/{itemId}";
    }
    
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        // 검증 로직
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null,
                                            "상품 이름은 필수입니다."));
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null,
                                            "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if(item.getQuantity() == null || item.getQuantity() < 0 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null,
                                            "수량은 최대 9,999 까지 허용합니다."));
        }

        // 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", null, null,
                                                "총 금액은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);

        // redirect 주소의 itemId로 바로 들어갈 수 있음
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        // 조회 화면으로 리다이렉트
        return "redirect:/validation/v2/items/{itemId}";
    }
    
//      @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        // 검증 로직
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false,
                                            new String[] { "required.item.itemName" }, null, null));
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false,
                                            new String[] { "range.item.price" }, new Object[] { 1000, 1000000 }, null));
        }

        if(item.getQuantity() == null || item.getQuantity() < 0 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false,
                                            new String[] { "max.item.quantity" }, new Object[] { 9999 }, null));
        }

        // 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", new String[] { "totalPriceMin" },
                                                new Object[] { 10000, resultPrice }, null));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);

        // redirect 주소의 itemId로 바로 들어갈 수 있음
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        // 조회 화면으로 리다이렉트
        return "redirect:/validation/v2/items/{itemId}";
    }
  
//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        // BindingResult는 이미 본인이 검증해야 할 객체인 target을 알고 있다.
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        // 검증 로직
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName", "required");
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[] { 1000, 1000000 }, null);
        }

        if(item.getQuantity() == null || item.getQuantity() < 0 || item.getQuantity() > 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[] { 9999 }, null);
        }

        // 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[] { 10000, resultPrice }, null);
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        // redirect 주소의 itemId로 바로 들어갈 수 있음
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        // 조회 화면으로 리다이렉트
        return "redirect:/validation/v2/items/{itemId}";
    }
    
//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        // BindingResult는 이미 본인이 검증해야 할 객체인 target을 알고 있다.
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());
        
        itemValidator.validate(item, bindingResult);

        // 검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        // redirect 주소의 itemId로 바로 들어갈 수 있음
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        // 조회 화면으로 리다이렉트
        return "redirect:/validation/v2/items/{itemId}";
    }
    
    /**
     * @Validated를 통해 바로 검증기 실행 가능
     */
    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        // BindingResult는 이미 본인이 검증해야 할 객체인 target을 알고 있다.
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());
        
        // 검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        // redirect 주소의 itemId로 바로 들어갈 수 있음
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        // 조회 화면으로 리다이렉트
        return "redirect:/validation/v2/items/{itemId}";
    }

    // 수정 폼을 보여만 주는 화면
    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") long itemId, Model model) {
        model.addAttribute("item", itemRepository.findById(itemId));
        return "validation/v2/editForm";
    }

    @PostMapping("{itemId}/edit")
    public String editItemV3(@PathVariable("itemId") long itemId, Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}
