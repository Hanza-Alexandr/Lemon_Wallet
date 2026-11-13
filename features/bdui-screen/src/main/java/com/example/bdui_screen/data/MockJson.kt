package com.example.bdui_screen.data

class MockJson {
}

val BDUI_INTERACTIVE = """
    {
      "components": [
        {
          "type": "ActionArea",
          "id": "action_copy_promo",
          "action": {
            "type": "CopyToClipboard",
            "text": "WINTER20"
          },
          "component": {
            "type": "TextBlock",
            "id": "text_promo_code",
            "text": "Нажмите, чтобы скопировать промокод WINTER20"
          }
        },
        {
          "type": "Spacer",
          "id": "spacer_int_1",
          "dp": 16
        },
        {
          "type": "ActionArea",
          "id": "action_open_browser",
          "action": {
            "type": "OpenLink",
            "uri": "https://example.com/rules"
          },
          "component": {
            "type": "ImageBlock",
            "id": "img_rules_banner",
            "url": "https://example.com/banners/rules.jpg"
          }
        },
        {
          "type": "Spacer",
          "id": "spacer_int_2",
          "dp": 16
        },
        {
          "type": "ActionArea",
          "id": "action_nav_cart",
          "action": {
            "type": "NavigateRoute",
            "route": "app://screens/cart"
          },
          "component": {
            "type": "HeaderBlock",
            "id": "header_go_cart",
            "header": "Перейти в корзину"
          }
        },
        {
          "type": "NewBackendFeature3DViewer",
          "id": "unknown_component_99",
          "model_url": "https://example.com/models/ring.obj",
          "auto_rotate": true
        }
      ]
    }
""".trimIndent()


val BDUI_MAIN_PROMO = """
    {
      "components": [
        {
          "type": "HeaderBlock",
          "id": "header_main",
          "header": "Новая коллекция"
        },
        {
          "type": "Spacer",
          "id": "spacer_1",
          "dp": 16
        },
        {
          "type": "TextBlock",
          "id": "text_desc",
          "text": "Специальные предложения, которые мы подобрали для вас."
        },
        {
          "type": "Spacer",
          "id": "spacer_2",
          "dp": 24
        },
        {
          "type": "HorizontalCarousel",
          "id": "carousel_top",
          "components": [
            {
              "type": "ImageBlock",
              "id": "img_banner_1",
              "url": "https://example.com/banners/item_1.png"
            },
            {
              "type": "ImageBlock",
              "id": "img_banner_2",
              "url": "https://example.com/banners/item_2.png"
            }
          ]
        },
        {
          "type": "Divider",
          "id": "divider_main"
        },
        {
          "type": "VerticalCarousel",
          "id": "vertical_list",
          "components": [
            {
              "type": "HeaderBlock",
              "id": "header_sub_1",
              "header": "Хит продаж"
            },
            {
              "type": "ImageBlock",
              "id": "img_hit_1",
              "url": "https://example.com/products/hit.jpg"
            }
          ]
        }
      ]
    }
""".trimIndent()