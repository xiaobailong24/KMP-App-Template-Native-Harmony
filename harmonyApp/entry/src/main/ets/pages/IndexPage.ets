import { helloKotlin } from '../../kotlinjs/kmp-shared';

@Entry
@Component
struct IndexPage {
  @State message: string = helloKotlin();

  build() {
    RelativeContainer() {
      Text(this.message)
        .id('IndexPageHelloWorld')
        .fontSize(50)
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
    }
    .height('100%')
    .width('100%')
  }
}