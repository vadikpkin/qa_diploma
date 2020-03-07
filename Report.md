# Отчет по итогам тестирования
## Краткое описание
Данный документ представляет собой отчет тестирования системы по оплате тура. Система состоит из Java приложения, mock сервиса, написанного на note.js, выполняющего роль банка (одобрение или отказ в проведении операции по карте) и базы данных для хранения информации о проведенных платежах. Заявлена поддержка MySQL и PostgresSQL.
Перечень использованных инструментов можно найти в [плане по тестированию](https://github.com/vadikpkin/qa_diploma/blob/master/Plan.md).
## Количество тест-кейсов
Было написано 13 тест кейсов. Из них 7 не выполняются по причине багов в системе.
##### Процент успешных тест-кейсов: 46%
## Общие рекомендации
По итогам тестирования можно выделить несколько багов, некоторые из которых критически влияют на работу системы.
 * High-priority баги:
   * [A success message pops up instead of a failure message during payment by card with status 'DECLINED'.](https://github.com/vadikpkin/qa_diploma/issues/2)
   * [A success message pops up instead of a failure message during payment by CREDIT card with status 'DECLINED'.](https://github.com/vadikpkin/qa_diploma/issues/3)
   * [Both success and failure messages pop up during payment by card with no status.](https://github.com/vadikpkin/qa_diploma/issues/4)
   * [Both success and failure messages pop up during payment by CREDIT card with no status.](https://github.com/vadikpkin/qa_diploma/issues/5)
 * Medium-priority баги:
 
      Баги валидарии поля 'Владелец':
   * [Payment with cirilyc chars in 'Owner' filed can be submitted.](https://github.com/vadikpkin/qa_diploma/issues/7)
   * [Payment with digits in 'Owner' filed can be submitted.](https://github.com/vadikpkin/qa_diploma/issues/8)
   * [Payment with special chars in 'Owner' filed can be submitted.](https://github.com/vadikpkin/qa_diploma/issues/9)
 * Low-priority баги:
   * [Text 'Wrong format' does not disappear after correct enter.](https://github.com/vadikpkin/qa_diploma/issues/11)
   * [Spelling mistake in word 'Маракэш' on startpage.](https://github.com/vadikpkin/qa_diploma/issues/6)
   
