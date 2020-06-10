Feature: Automation of Add Segment Details 

Scenario: Opens the Browser 
	Given Driver will open Browser 
	
Scenario Outline: Login into URL by entering valid credentials 
	Given Enters the URL with "<username>" and "<password>" 
	
	Examples: 
		| username | password |
		| p8admin  | mits123$ |
		
Scenario: Selects existing Rows performs delete opearation 
	Then selects all rows and clicks on subtract button 
	
	#Scenario: Select Add button 
	#	Then   Clicks on Add button 
	
Scenario: Data functionality 
	Then User enters data in porperties 
		| GroupName | SegmentName | ClientBrand       | Channel             | OfferType | SegmentProductType | SegmentOfferDescription | TargetingCriteria | SegmentOfferStartDate | SegmentOfferEndDate | CardTierType | OfferLocation | Language | MerchantCategory | Test/Control | Segment% | SegmentCount(0) | RemailCounter | EmailSubject | EmailPre-Header | CreativeCode | CISMemo  | Comments |
		| Group 09  | F           | Amazon Commercial | Email & Direct Mail | Gifts     | DUAL CARD          | Breaking Point          | Liquidity         | 05/15/2020            | 05/22/2020          | All          | Instore       | Language | Entertainment    | Control      |        0 |             123 |             5 | DC - Upgrade | Data passage    | Call Coffee  | Call Tea | Karen    |
		
Scenario: Select save button after adding data to properties 
	Given User Clicks save button 
	
Scenario: closing the Browser 
	Then Driver closes the browser 
	
Scenario Outline: Negative scenarions 
	Given Enters the URL with invalid username "<username>" and "<password>" 
	
	Examples: 
		| username | password   |
		| p8admin  | mit12s123$ |
		
Scenario: closing the Browser 
	Then Driver closes the browser 
