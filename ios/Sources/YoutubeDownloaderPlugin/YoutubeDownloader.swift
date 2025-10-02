import Foundation

@objc public class AudioClient: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
