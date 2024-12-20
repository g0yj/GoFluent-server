package com.lms.api.client.holiday.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemsDeserializer extends JsonDeserializer<HolidayResponse.Items> {

  @Override
  public HolidayResponse.Items deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonNode node = p.getCodec().readTree(p);
    List<HolidayResponse.Item> itemsList = new ArrayList<>();

    // item이 배열일 경우 처리
    if (node.has("item")) {
      JsonNode itemNode = node.get("item");

      if (itemNode.isArray()) {
        for (JsonNode objNode : itemNode) {
          HolidayResponse.Item item = p.getCodec().treeToValue(objNode, HolidayResponse.Item.class);
          itemsList.add(item);
        }
      } else {
        // item이 단일 객체일 경우 처리
        HolidayResponse.Item item = p.getCodec().treeToValue(itemNode, HolidayResponse.Item.class);
        itemsList.add(item);
      }
    }

    // Items 객체 생성 및 반환
    HolidayResponse.Items items = new HolidayResponse.Items();
    items.setItemList(itemsList); // 아이템 리스트 설정
    return items;
  }
}
