from django.test import TestCase
from .facebook import getFacebookEvents

# Create your tests here.

page_events = getFacebookEvents("ArcUNSW", 10)
print(str(page_events))

# class FacebookTestCase(TestCase):
#     def Should_ReturnAllPageEvents_Given_PageName(self):
#         page_events = getFacebookEvents("")
#         self.assertNotEqual(page_events, None)
