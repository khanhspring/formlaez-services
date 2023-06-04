package com.formlaez.infrastructure.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormFieldType {
    Text(false),
    Image(false),
    Video(false),
    Pdf(false),
    Line(false),
    QRCode(false),
    TwitterTweet(false),

    InputText(true),
    InputNumber(true),
    Datetime(true),
    LongText(true),
    Email(true),
    Rating(true),
    OpinionScale(true),
    Switch(true),
    Dropdown(true),
    MultipleChoice(true),
    Signature(true),

    StatusList(true),
    InputMarkdown(true),
    InputUrl(true);
    
    private final boolean formControl;
}
