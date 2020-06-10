Feature: Automation Case Manager functions 

#This opens the browser
Scenario: Opening Brower 
	Given User will open Browser 
#This enter's the url page and login with 

Scenario Outline: Login into URl 
	Given Enters the URL with "<username>" and "<password>" 
	
	Examples: 
		| username | password |
		| p8admin  | mits123$ |

#Delete's the row's avalible.   		
Scenario: Select Subtract button 


	Then   user selects all rows and clicks on subtract button 
	
#	Add row's 
Scenario: Select Add button 
	Then   Clicks on Add button 
	
#We Enter the data in a property field in this scenario.	
Scenario: Data functionality 
	Then User enters data in porperties 
	
		| GroupName| SegmentName | ClientBrand         |Channel             |OfferType      |SegmentProductType |SegmentOfferDescription      |  TargetingCriteria     |  SegmentOfferStartDate  | SegmentOfferEndDate  | CardTierType  | OfferLocation  | Language    | MerchantCategory | Test/Control   | Segment%| SegmentCount(0)  | RemailCounter |  EmailSubject| EmailPre-Header| CreativeCode| CISMemo       |Comments   |
		| Group 09 | F           | Amazon Commercial   |Email & Direct Mail|      Gifts    |DUAL CARD          |      Breaking Point           | Liquidity               | 05/15/2020              |05/22/2020            |   All      |Instore         | Language    | Entertainment    | Control        |      0  | 123              |  5            | DC - Upgrade | Data passage   | Call Coffee | Call Tea      | Karen     |
		
		
#Save's the entire data		
Scenario: Select save button after filling data 
	Given User Clicks save button 
	
	
#Closes the browser	
Scenario: closing Browser 
	Then User closes the browser 
	
	
	
#We execute a negative Scenario here.	
Scenario Outline: Negative scenarions 
	Given Enters the URL with invalid username "<username>" and "<password>" 
	
	Examples: 
		| username | password |
		| p8admin  | mit12s123$ | 